package com.example.phungloc.controllers;

import com.example.phungloc.dto.request.invoice_request.CreateInvoiceRequest;
import com.example.phungloc.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice")
@CrossOrigin(origins = "http://localhost:5173")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/sync")
    public ResponseEntity<?> syncInvoice(@RequestBody CreateInvoiceRequest request) {
        return invoiceService.syncInvoice(request);
    }

}
