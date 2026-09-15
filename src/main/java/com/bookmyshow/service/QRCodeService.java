package com.bookmyshow.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QRCodeService {

    private static final String QR_FOLDER = "tickets/qr";

    public String generateQRCode(
            String bookingRef,
            String userEmail,
            Double totalAmount) throws Exception {

        // -----------------------------------------------------
        // QR code content
        // -----------------------------------------------------

        String qrContent =
                "Booking Reference: " + bookingRef
                        + "\n"
                        + "User Email: " + userEmail
                        + "\n"
                        + "Amount: ₹" + totalAmount;


        // -----------------------------------------------------
        // Create QR folder
        // -----------------------------------------------------

        Path folder =
                Paths.get(QR_FOLDER);

        Files.createDirectories(folder);


        // -----------------------------------------------------
        // File path
        // -----------------------------------------------------

        Path filePath =
                folder.resolve(
                        bookingRef + ".png"
                );


        // -----------------------------------------------------
        // Generate QR
        // -----------------------------------------------------

        BitMatrix bitMatrix =
                new MultiFormatWriter().encode(
                        qrContent,
                        BarcodeFormat.QR_CODE,
                        300,
                        300
                );


        // -----------------------------------------------------
        // Save PNG
        // -----------------------------------------------------

        MatrixToImageWriter.writeToPath(
                bitMatrix,
                "PNG",
                filePath
        );


        // -----------------------------------------------------
        // Return path
        // -----------------------------------------------------

        return filePath.toString();
    }
}