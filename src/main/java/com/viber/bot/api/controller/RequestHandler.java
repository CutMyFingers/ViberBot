package com.viber.bot.api.controller;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.CharStreams;
import com.viber.bot.Request;
import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;

@RestController
public class RequestHandler {
    @Autowired
    private ViberBot bot;

    @Autowired
    private ViberSignatureValidator signatureValidator;

    @PostMapping(value = "/", produces = "application/json")
    public String incoming(@RequestBody String json, @RequestHeader(
        "X-Viber-Content-Signature"
    ) String serverSideSignature) throws ExecutionException, InterruptedException, IOException {

        Preconditions.checkState(signatureValidator.isSignatureValid(serverSideSignature, json), "invalid signature");
        InputStream response = bot.incoming(Request.fromJsonString(json)).get();
        return response != null ? CharStreams.toString(new InputStreamReader(response, Charsets.UTF_16)) : null;
    }
}
