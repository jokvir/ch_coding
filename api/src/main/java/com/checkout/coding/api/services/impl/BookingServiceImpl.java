package com.checkout.coding.api.services.impl;

import com.checkout.coding.api.dto.AvailabilityRequest;
import com.checkout.coding.api.dto.BookingRequest;
import com.checkout.coding.api.entities.Availability;
import com.checkout.coding.api.entities.Booking;
import com.checkout.coding.api.entities.BookingDetail;
import com.checkout.coding.api.entities.Session;
import com.checkout.coding.api.repositories.AvailabilityRepository;
import com.checkout.coding.api.repositories.BookingDetailRepository;
import com.checkout.coding.api.repositories.BookingRepository;
import com.checkout.coding.api.repositories.SessionRepository;
import com.checkout.coding.api.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final AvailabilityRepository availabilityRepository;
    private final BookingDetailRepository bookingDetailRepository;
    private final SessionRepository sessionRepository;

    private final JavaMailSender mailSender;


    @Override
    public Booking createBooking(BookingRequest request) {
        if (request.customerName() == null || request.email() == null || request.phone() == null) {
            throw new IllegalArgumentException("Missing customer details");
        }

        List<AvailabilityRequest> slots = request.availabilities();
        if (slots.isEmpty()) {
            throw new IllegalArgumentException("No valid slots selected");
        }
        List<Session> sessions = slots.stream()
                .map(slot -> sessionRepository.findById(slot.sessionId()).orElse(null))
                .filter(Objects::nonNull)
                .toList();

        BigDecimal totalPrice = sessions.stream()
                .map(Session::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Booking booking = Booking.builder()
                .customerName(request.customerName())
                .customerEmail(request.email())
                .customerPhone(request.phone())
                .totalPrice(totalPrice)
                .build();

        bookingRepository.save(booking);

        List<Availability> availabilities = slots.stream()
                .map(a -> Availability.builder()
                        .startTime(a.startTime())
                        .endTime(a.endTime())
                        .session(sessionRepository.findById(a.sessionId()).orElseThrow(() -> new RuntimeException("Session not found")))
                        .build())
                .map(availabilityRepository::save)
                .toList();

        availabilities.forEach(availability -> {
            BookingDetail detail = BookingDetail.builder()
                    .booking(booking)
                    .availability(availability)
                    .duration(request.duration())
                    .price(totalPrice)
                    .build();
            bookingDetailRepository.save(detail);
        });


        StringBuilder emailBody = new StringBuilder();



        sendEmail(request.email(), "Booking requested", buildBookingEmail(request, totalPrice));
        return booking;
    }




    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("your-email@gmail.com");

        mailSender.send(message);
    }

    private String buildBookingEmail(BookingRequest request, BigDecimal totalPrice) {
        StringBuilder emailBody = new StringBuilder()
                .append("Hello ").append(request.customerName()).append(",\n\n")
                .append("Thank you for your booking request!\n\n")
                .append("Here are the details of your reservation:\n")
                .append("--------------------------------------\n")
                .append("📞 Phone: ").append(request.phone()).append("\n")
                .append("📧 Email: ").append(request.email()).append("\n")
                .append("⏳ Duration: ").append(request.duration()).append(" minutes\n\n")
                .append("🔹 Selected Sessions:\n");

        request.availabilities().forEach(availability ->
                emailBody.append("   📅 Date: ").append(availability.startTime().toLocalDate()).append("\n")
                        .append("   ⏰ Time: ").append(availability.startTime().toLocalTime()).append(" - ")
                        .append(availability.endTime().toLocalTime()).append("\n")
                        .append("   🎾 Session ID: ").append(availability.sessionId()).append("\n")
                        .append("   --------------------------------------\n")
        );

        emailBody.append("\n💳 Total Amount: ").append(totalPrice).append(" €\n")
                .append("✅ Your booking is currently being processed.\n")
                .append("You will receive a confirmation email once it is validated.\n\n")
                .append("If you have any questions, feel free to contact us.\n\n")
                .append("Best regards,\n")
                .append("🏋️ Your Booking Team");

        return emailBody.toString();
    }


}

