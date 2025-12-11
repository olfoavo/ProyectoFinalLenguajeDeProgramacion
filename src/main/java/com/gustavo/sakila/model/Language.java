package com.gustavo.sakila.model;

import java.time.Instant;

public record Language(int id, String name, Instant lastUpdate) {}