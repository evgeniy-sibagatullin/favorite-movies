package com.example.sib.liquibase;

import io.quarkus.liquibase.LiquibaseFactory;
import liquibase.Liquibase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class MigrationService {

    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    LiquibaseFactory liquibaseFactory;

    public void checkMigration() {
        // Use the liquibase instance manually
        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {
            liquibase.dropAll();
            liquibase.validate();
            liquibase.update(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
            // Get the list of liquibase change set statuses
            // List<ChangeSetStatus> status = liquibase.getChangeSetStatuses(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}