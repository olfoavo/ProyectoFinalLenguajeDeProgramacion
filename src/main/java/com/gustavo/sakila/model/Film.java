package com.gustavo.sakila.model;

import java.time.Instant;

public record Film(int id, String title, Integer languageId, Instant lastUpdate) {}