<template>
    <div class="agenda-wrapper">
        <FullCalendar :options="calendarOptions" />
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import FullCalendar from '@fullcalendar/vue3'

import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import ptBrLocale from '@fullcalendar/core/locales/pt-br'

import api from '../services/api'

const eventos = ref([])

const calendarOptions = ref({
    plugins: [
        timeGridPlugin,
        interactionPlugin
    ],

    locale: ptBrLocale,
    initialView: 'timeGridWeek',
    
    slotMinTime: '08:00:00',
    slotMaxTime: '19:00:00',
    allDaySlot: false,
    expandRows: true, 
    height: '100%',
    
    nowIndicator: true,

    buttonText: {
        today: 'Hoje',
        week: 'Semana',
        day: 'Dia'
    },

    headerToolbar: {
        left: 'prev,next today',
        center: 'title',
        right: 'timeGridWeek,timeGridDay'
    },

    events: eventos.value
})

const carregarAgendamentos = async () => {
    try {
        const response = await api.get('/agendamentos')

        eventos.value = response.data.map(a => ({
            title: a.cliente.nome,
            start: a.dataHoraInicio,
            end: a.dataHoraFim,
            backgroundColor: a.status === 'CANCELADO' ? 'rgba(239, 68, 68, 0.15)' : 'rgba(250, 204, 21, 0.15)',
            borderColor: a.status === 'CANCELADO' ? '#ef4444' : '#facc15',
            textColor: a.status === 'CANCELADO' ? '#fca5a5' : '#fef08a'
        }))

        calendarOptions.value.events = eventos.value

    } catch (error) {
        console.error(error)
    }
}

onMounted(() => {
    carregarAgendamentos()
})
</script>

<style scoped>

.agenda-wrapper {
    height: 750px;
    background: #111112;
    border-radius: 24px;
    padding: 24px;
    border: 1px solid rgba(255, 255, 255, 0.05);
    box-shadow: 0 20px 50px rgba(0, 0, 0, 0.4);

    --fc-border-color: rgba(255, 255, 255, 0.08);
    --fc-page-bg-color: transparent;
    --fc-neutral-bg-color: #1a1a1c;
    --fc-neutral-text-color: #9ca3af;
    --fc-today-bg-color: rgba(250, 204, 21, 0.03); 
    --fc-now-indicator-color: #facc15; 
}

/* ==========================================
   2. TOOLBAR (CABEÇALHO E BOTÕES)
   ========================================== */
:deep(.fc-toolbar-title) {
    font-family: 'Poppins', sans-serif;
    font-size: 24px !important;
    font-weight: 700;
    color: #fff;
    text-transform: capitalize;
}

:deep(.fc-button-primary) {
    background-color: transparent !important;
    border: 1px solid rgba(255, 255, 255, 0.15) !important;
    color: #fff !important;
    text-transform: capitalize;
    font-family: 'Poppins', sans-serif;
    font-weight: 500;
    border-radius: 10px !important;
    padding: 8px 16px !important;
    transition: 0.3s;
    box-shadow: none !important;
}

/* Efeito Hover e Ativo nos Botões */
:deep(.fc-button-primary:not(:disabled):hover),
:deep(.fc-button-primary:not(:disabled).fc-button-active) {
    background-color: #facc15 !important;
    border-color: #facc15 !important;
    color: #111 !important;
}


:deep(.fc-col-header-cell) {
    padding: 12px 0;
    background-color: rgba(255, 255, 255, 0.02);
}

:deep(.fc-col-header-cell-cushion) {
    color: #fff;
    font-weight: 600;
    font-size: 15px;
    text-decoration: none !important;
}

:deep(.fc-timegrid-slot-label-cushion) {
    color: #9ca3af;
    font-size: 13px;
}

:deep(.fc-timegrid-slot) {
    height: 45px !important;
}

:deep(.fc-now-indicator-arrow) {
    border-width: 6px 0 6px 7px;
    border-top-color: transparent;
    border-bottom-color: transparent;
}


:deep(.fc-event) {
    border-radius: 8px;
    border-left-width: 4px; 
    padding: 4px 6px;
    font-size: 13px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    transition: transform 0.2s, box-shadow 0.2s;
    cursor: pointer;
    overflow: hidden;
}

:deep(.fc-event:hover) {
    transform: translateY(-2px);
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.3);
    z-index: 5 !important;
}

:deep(.fc-event-title) {
    font-weight: 600;
    font-family: 'Poppins', sans-serif;
}

:deep(.fc-event-time) {
    font-size: 11px;
    opacity: 0.8;
    margin-bottom: 2px;
}

:deep(.fc) {
    height: 100% !important;
    font-family: inherit;
}

:deep(.fc-scroller) {
    overflow-y: auto !important;
}
:deep(.fc-scroller::-webkit-scrollbar) {
    width: 8px;
}
:deep(.fc-scroller::-webkit-scrollbar-track) {
    background: rgba(255, 255, 255, 0.02);
}
:deep(.fc-scroller::-webkit-scrollbar-thumb) {
    background: rgba(255, 255, 255, 0.1);
    border-radius: 10px;
}
</style>