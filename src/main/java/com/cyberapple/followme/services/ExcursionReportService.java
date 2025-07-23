package com.cyberapple.followme.services;

import com.cyberapple.followme.dtos.ExcursionDto;
import com.cyberapple.followme.dtos.ExcursionParticipantsDto;
import com.cyberapple.followme.entities.Participant;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcursionReportService {
    private final ExcursionService excursionService;

    public void generateParticipantReport(String excursionId, HttpServletResponse response) throws IOException {
        ExcursionParticipantsDto excursionParticipants = excursionService.getExcursionParticipants(excursionId);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"report.xlsx\"");

        try (Workbook workbook = new XSSFWorkbook()) {

            ExcursionDto excursion = excursionParticipants.getExcursion();
            List<Participant> participants = excursionParticipants.getParticipants();

            Sheet excursionInfoSheet = workbook.createSheet("Excursion Info");

            Row rowTitle = excursionInfoSheet.createRow(0);
            rowTitle.createCell(0).setCellValue("Excursion Name");
            rowTitle.createCell(1).setCellValue(excursion.getTitle());

            Row rowDate = excursionInfoSheet.createRow(1);
            rowDate.createCell(0).setCellValue("Date");
            rowDate.createCell(1).setCellValue(excursion.getDate().toString());

            Row rowPrice = excursionInfoSheet.createRow(2);
            rowPrice.createCell(0).setCellValue("Price");
            rowPrice.createCell(1).setCellValue(excursion.getPrice().toString());

            Row rowCountry = excursionInfoSheet.createRow(3);
            rowCountry.createCell(0).setCellValue("Country");
            rowCountry.createCell(1).setCellValue(excursion.getCountry().toString());

            Row rowAmountOfPlaces = excursionInfoSheet.createRow(4);
            rowAmountOfPlaces.createCell(0).setCellValue("Amount of Places");
            rowAmountOfPlaces.createCell(1).setCellValue(excursion.getAmountOfPlaces().toString());

            Row rowAvailablePlaces = excursionInfoSheet.createRow(5);
            rowAvailablePlaces.createCell(0).setCellValue("Available Places");
            rowAvailablePlaces.createCell(1).setCellValue(excursion.getAvailablePlaces().toString());

            Sheet participantsSheet = workbook.createSheet("Participants");
            Row header1 = participantsSheet.createRow(0);
            header1.createCell(0).setCellValue("First Name");
            header1.createCell(1).setCellValue("Last Name");
            header1.createCell(2).setCellValue("Date of Birth");
            header1.createCell(3).setCellValue("Citizenship");
            header1.createCell(4).setCellValue("Passport Number");

            int rowIdx1 = 1;
            for (Participant participant: participants) {
                Row row = participantsSheet.createRow(rowIdx1++);
                row.createCell(0).setCellValue(participant.getLastName());
                row.createCell(1).setCellValue(participant.getFirstName());
                row.createCell(2).setCellValue(participant.getDateOfBirth().toString());
                row.createCell(3).setCellValue(participant.getCitizenship().toString());
                row.createCell(4).setCellValue(participant.getPassportNumber());
            }

            workbook.write(response.getOutputStream());
        }
    }
}
