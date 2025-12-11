package com.gustavo.sakila.model;

import java.time.Instant;

public record Category(int id, String name, Instant lastUpdate) {}