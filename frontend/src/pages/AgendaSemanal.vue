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

    expandRows: false,

    height: '100%',

    contentHeight: 550,

    events: eventos.value
})

const carregarAgendamentos = async () => {
    try {

        const response = await api.get('/agendamentos')

        eventos.value = response.data.map(a => ({
            title: a.cliente.nome,
            start: a.dataHoraInicio,
            end: a.dataHoraFim,
            color:
                a.status === 'CANCELADO'
                    ? '#ef4444'
                    : '#22c55e'
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
    height: 700px;
    overflow: hidden;
    background: #111;
    border-radius: 24px;
    padding: 20px;
}

/* calendário inteiro */
:deep(.fc) {
    height: 100% !important;
}

/* área principal */
:deep(.fc-view-harness) {
    height: 550px !important;
}

/* scroll interno */
:deep(.fc-scroller) {
    overflow-y: auto !important;
}

/* células de horário */
:deep(.fc-timegrid-slot) {
    height: 40px !important;
}

/* toolbar */
:deep(.fc-toolbar-title) {
    font-size: 20px;
}

/* eventos */
:deep(.fc-event) {
    border-radius: 10px;
    border: none;
    padding: 2px 6px;
    font-size: 13px;
}
</style>