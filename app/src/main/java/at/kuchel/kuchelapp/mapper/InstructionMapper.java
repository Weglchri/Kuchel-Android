package at.kuchel.kuchelapp.mapper;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.api.Instruction;
import at.kuchel.kuchelapp.model.InstructionEntity;

/**
 * Created by bernhard on 19.03.2018.
 */

public class InstructionMapper {

    public static List<InstructionEntity> map(Long recipeId, List<Instruction> instructionRespons) {
        List<InstructionEntity> instructionEntities = new ArrayList<>();
        for (Instruction instruction : instructionRespons) {
            instructionEntities.add(map(recipeId, instruction));
        }
        return instructionEntities;
    }

    private static InstructionEntity map(Long recipeId, Instruction instruction) {
        InstructionEntity instructionEntity = new InstructionEntity();
        instructionEntity.setDescription(instruction.getDescription());
        instructionEntity.setStep(instruction.getStep());
        instructionEntity.setRecipeId(Integer.valueOf(String.valueOf(recipeId)));
        return instructionEntity;
    }
}
