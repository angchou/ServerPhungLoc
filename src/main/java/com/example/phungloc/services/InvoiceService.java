package com.example.phungloc.services;

import com.example.phungloc.dto.request.invoice_request.CreateInvoiceRequest;
import org.springframework.http.ResponseEntity;

public interface InvoiceService {
    ResponseEntity<?> syncInvoice(CreateInvoiceRequest request);
}
