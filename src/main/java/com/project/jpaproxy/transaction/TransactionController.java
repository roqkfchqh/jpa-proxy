package com.project.jpaproxy.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("t/{id}/{name}")
    public ResponseEntity<String> transaction(
            @PathVariable Long id,
            @PathVariable String name
    ) {
        transactionService.updateUser(id, name);
        return ResponseEntity.ok("ㅎㅇ");
    }

    @PostMapping("p/{name}")
    public ResponseEntity<String> transaction(
            @PathVariable String name
    ){
        transactionService.outer(name);
        return ResponseEntity.ok("ㅎㅇ");
    }
}
