package com.coursealura.med.vollapi.model.dtos;

import com.coursealura.med.vollapi.model.Adress;
import jakarta.validation.constraints.NotNull;

public record PatientUpdateData(@NotNull Long id,
                                String name,
                                String phone,
                                Adress adress) {
}
