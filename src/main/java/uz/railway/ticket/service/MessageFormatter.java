package uz.railway.ticket.service;

import org.springframework.stereotype.Component;
import uz.railway.ticket.dto.TrainDto;

@Component
public class MessageFormatter {

    public String formatTicketMessage(TrainDto train) {
        StringBuilder message = new StringBuilder();
        message.append("🚂 <b>TICKET TOPILDI!</b>\n\n");
        message.append(String.format("🚆 <b>Poyezd:</b> %s (%s)\n", train.getNumber(), train.getBrand()));
        message.append(String.format("📍 <b>Yo'nalish:</b> %s → %s\n", train.getSubRoute().getDepStationName(), train.getSubRoute().getArvStationName()));
        message.append(String.format("🕐 <b>Jo'nash:</b> %s\n", train.getDepartureDate()));
        message.append(String.format("🕐 <b>Kelish:</b> %s\n", train.getArrivalDate()));
        message.append(String.format("⏱ <b>Yo'lda:</b> %s\n\n", train.getTimeOnWay()));

        message.append("💺 <b>Bo'sh joylar:</b>\n");
        train.getCars().forEach(car -> {
            if (car.getFreeSeats() > 0) {
                message.append(String.format("  • <b>%s:</b> %d ta\n", car.getType(), car.getFreeSeats()));
                car.getTariffs().forEach(
                        tariff -> message.append(String.format("    💰 Narx: <i>%.0f so'm</i>\n", tariff.getTariff()))
                );
            }
        });

        return message.toString();
    }
}
