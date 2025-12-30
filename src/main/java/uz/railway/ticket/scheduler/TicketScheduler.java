package uz.railway.ticket.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.railway.ticket.dto.CarDto;
import uz.railway.ticket.dto.Station;
import uz.railway.ticket.dto.TrainDto;
import uz.railway.ticket.service.MessageFormatter;
import uz.railway.ticket.service.RailwayApiService;
import uz.railway.ticket.service.TelegramService;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketScheduler {

    private final RailwayApiService railwayApiService;
    private final TelegramService telegramService;
    private final MessageFormatter messageFormatter;

    @Scheduled(fixedDelay = 15_000)
    public void checkTickets(){
        this.search("2025-12-30", Station.TASHKENT, Station.MISKIN);
        this.search("2026-01-04", Station.MISKIN, Station.TASHKENT);
    }

    private void search(String travelDate, Station from, Station to) {
        log.info("🔍 Qidiruv boshlandi: {} → {}, sana: {}", from.getName(), to.getName(), travelDate);

        List<TrainDto> trains = railwayApiService.searchTickets(from.getCode(), to.getCode(), travelDate);
        if (trains.isEmpty()) {
            log.info("❌ Poyezdlar topilmadi");
            return;
        }

        trains.forEach(train -> {
            int totalSeats = train.getCars().stream()
                    .mapToInt(CarDto::getFreeSeats)
                    .sum();

            if (totalSeats > 0) {
                log.info("✅ Ticket topildi: {}", train.getNumber());
                String message = messageFormatter.formatTicketMessage(train);
                telegramService.sendMessage(message);
            } else {
                log.info("❌ Ticket topilmadi: 🤷🏻‍♂️");
            }
        });

        try {
            Thread.sleep(Duration.ofSeconds(3));
        } catch (InterruptedException e) {
            log.error("🔥InterruptedException");
        }
    }
}
