package com.example.tccbarbearia.barberia.service;

import com.example.tccbarbearia.barberia.dto.AgendamentoRequest;
import com.example.tccbarbearia.barberia.dto.DisponibilidadeResponse;
import com.example.tccbarbearia.barberia.entity.*;
import com.example.tccbarbearia.barberia.exception.BusinessException;
import com.example.tccbarbearia.barberia.repository.AgendamentoRepository;
import com.example.tccbarbearia.barberia.repository.ClienteRepository;
import com.example.tccbarbearia.barberia.repository.HorarioTrabalhoRepository;
import com.example.tccbarbearia.barberia.repository.UsuarioRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteService clienteService;
    private final ServicoService servicoService;
    private final HorarioTrabalhoRepository horarioTrabalhoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            ClienteService clienteService,
            ServicoService servicoService,
            HorarioTrabalhoRepository horarioTrabalhoRepository,
            UsuarioRepository usuarioRepository,
            ClienteRepository clienteRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteService = clienteService;
        this.servicoService = servicoService;
        this.horarioTrabalhoRepository = horarioTrabalhoRepository;
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Agendamento> listar() {
        return agendamentoRepository.findAll();
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Agendamento não encontrado"));
    }

    public Agendamento criar(AgendamentoRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuarioLogado = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Cliente clienteParaAgendar;

        if (usuarioLogado.getPerfil() == Perfil.ADMIN) {
            clienteParaAgendar = clienteService.buscarPorId(request.getClienteId());
        }

        else {
            clienteParaAgendar = clienteRepository.findByUsuario(usuarioLogado)
                    .orElseThrow(() -> new BusinessException("Cliente não encontrado"));
        }

        Servico servico = servicoService.buscarPorId(request.getServicoId());

        LocalDateTime inicio = request.getDataHoraInicio();
        LocalDateTime fim = inicio.plusMinutes(servico.getDuracaoMinutos());

        validarHorarioDeTrabalho(inicio.toLocalDate(), inicio.toLocalTime(), fim.toLocalTime());
        validarConflito(inicio, fim);

        Agendamento agendamento = Agendamento.builder()
                .cliente(clienteParaAgendar)
                .servico(servico)
                .dataHoraInicio(inicio)
                .dataHoraFim(fim)
                .status(StatusAgendamento.AGENDADO)
                .observacoes(request.getObservacoes())
                .build();

        return agendamentoRepository.save(agendamento);
    }

    public Agendamento atualizarStatus(Long id, StatusAgendamento status) {
        Agendamento agendamento = buscarPorId(id);
        agendamento.setStatus(status);
        return agendamentoRepository.save(agendamento);
    }

    public List<DisponibilidadeResponse> consultarDisponibilidade(LocalDate data, Long servicoId) {
        Servico servico = servicoService.buscarPorId(servicoId);

        int diaSemana = data.getDayOfWeek().getValue();
        List<HorarioTrabalho> horarios = horarioTrabalhoRepository.findByDiaSemanaAndAtivoTrue(diaSemana);

        if (horarios.isEmpty()) {
            return List.of();
        }

        List<DisponibilidadeResponse> resposta = new ArrayList<>();
        LocalDateTime inicioDia = data.atStartOfDay();
        LocalDateTime fimDia = data.atTime(LocalTime.MAX);

        List<Agendamento> agendamentosDia = agendamentoRepository.findByDataHoraInicioBetween(inicioDia, fimDia);

        for (HorarioTrabalho faixa : horarios) {
            LocalTime atual = faixa.getHoraInicio();

            while (!atual.plusMinutes(servico.getDuracaoMinutos()).isAfter(faixa.getHoraFim())) {
                LocalDateTime inicioSlot = LocalDateTime.of(data, atual);
                LocalDateTime fimSlot = inicioSlot.plusMinutes(servico.getDuracaoMinutos());

                boolean ocupado = agendamentosDia.stream()
                        .filter(a -> a.getStatus() == StatusAgendamento.AGENDADO)
                        .anyMatch(a -> inicioSlot.isBefore(a.getDataHoraFim()) &&
                                fimSlot.isAfter(a.getDataHoraInicio()));

                resposta.add(new DisponibilidadeResponse(atual, !ocupado));
                atual = atual.plusMinutes(30);
            }
        }

        return resposta;
    }

    private void validarHorarioDeTrabalho(LocalDate data, LocalTime inicio, LocalTime fim) {
        int diaSemana = data.getDayOfWeek().getValue();
        List<HorarioTrabalho> horarios = horarioTrabalhoRepository.findByDiaSemanaAndAtivoTrue(diaSemana);

        boolean dentroDoHorario = horarios.stream().anyMatch(h -> !inicio.isBefore(h.getHoraInicio()) &&
                !fim.isAfter(h.getHoraFim()));

        if (!dentroDoHorario) {
            throw new BusinessException("Horário fora do expediente");
        }
    }

    private void validarConflito(LocalDateTime inicio, LocalDateTime fim) {
        List<Agendamento> conflitos = agendamentoRepository
                .findByStatusInAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
                        List.of(StatusAgendamento.AGENDADO),
                        fim,
                        inicio);

        if (!conflitos.isEmpty()) {
            throw new BusinessException("Já existe agendamento nesse horário");
        }
    }

    public List<Agendamento> listarDoClienteLogado() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Cliente cliente = clienteRepository.findByUsuario(usuario)
                .orElseThrow(() -> new BusinessException("Cliente não encontrado"));

        return agendamentoRepository.findByClienteId(cliente.getId());

    }

    public Agendamento remarcarAgendamento(Long id, AgendamentoRequest request) {
        Agendamento agendamento = buscarPorId(id);

        Servico servico = agendamento.getServico();

        LocalDateTime novoInicio = request.getDataHoraInicio();
        LocalDateTime novoFim = novoInicio.plusMinutes(servico.getDuracaoMinutos());

        validarHorarioDeTrabalho(
                novoInicio.toLocalDate(),
                novoInicio.toLocalTime(),
                novoFim.toLocalTime());

        validarConflito(novoInicio, novoFim);

        agendamento.setDataHoraInicio(novoInicio);
        agendamento.setDataHoraFim(novoFim);
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        return agendamentoRepository.save(agendamento);
    }

    public Agendamento cancelarAgendamento(Long id) {
        Agendamento agendamento = buscarPorId(id);
        agendamento.setStatus(StatusAgendamento.CANCELADO);
        return agendamentoRepository.save(agendamento);
    }

}