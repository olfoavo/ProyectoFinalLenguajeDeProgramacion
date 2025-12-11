package com.gustavo.sakila;

import com.gustavo.sakila.repo.LanguageRepository;
import com.gustavo.sakila.repo.CategoryRepository;
import com.gustavo.sakila.repo.FilmRepository;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Demo Sakila: Language + Category + Film (CRUD) ===");
        LanguageRepository langRepo = new LanguageRepository();
        CategoryRepository catRepo = new CategoryRepository();
        FilmRepository filmRepo = new FilmRepository();

        try {
            long langs = langRepo.count();
            long cats  = catRepo.count();
            long films = filmRepo.count();
            System.out.println("Registros -> language: " + langs + " | category: " + cats + " | film: " + films);

            // CRUD sencillo en language (tabla ligera)
            int id = langRepo.insert("NEO");
            System.out.println("Creado language_id=" + id);

            boolean up = langRepo.updateName(id, "ANDERSON");
            System.out.println("Actualizado? " + up);

            boolean del = langRepo.delete(id);
            System.out.println("Borrado? " + del);

            System.out.println("OK.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}