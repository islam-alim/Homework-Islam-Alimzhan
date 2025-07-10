package com.andersen.islam.hw1;

import java.util.Optional;

public class IdentifyService {
    private final WorkspaceRepositoryJpa workspaceRepositoryJpa;

    public IdentifyService(WorkspaceRepositoryJpa workspaceRepositoryJpa) {
        this.workspaceRepositoryJpa = workspaceRepositoryJpa;
    }

    public Optional<String> getType(int workspaceId) {
        return checkIsAnEternalConsultant(workspaceId)
                ? Optional.of("External consultant")
                : workspaceRepositoryJpa.getTypeById(workspaceId);
    }

    private boolean checkIsAnEternalConsultant(int workspaceId) {
        return workspaceId>=100;
    }

}
