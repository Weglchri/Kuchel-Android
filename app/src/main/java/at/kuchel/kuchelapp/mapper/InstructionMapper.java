package at.kuchel.kuchelapp.mapper;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.api.Instruction;
import at.kuchel.kuchelapp.model.InstructionEntity;

/**
 * Created by bernhard on 19.03.2018.
 */

public class InstructionMapper {

    public static List<InstructionEntity> mapToEntity(Long recipeId, List<Instruction> instructionRespons) {
        List<InstructionEntity> instructionEntities = new ArrayList<>();
        for (Instruction instruction : instructionRespons) {
            instructionEntities.add(mapToEntity(recipeId, instruction));
        }
        return instructionEntities;
    }

    public static List<Instruction> mapToApi(Long recipeId, List<InstructionEntity> instructionEntities) {
        List<Instruction> instructions = new ArrayList<>();
        for (InstructionEntity instructionEntity : instructionEntities) {
            instructions.add(mapToApi(instructionEntity));
        }
        return instructions;
    }

    private static InstructionEntity mapToEntity(Long recipeId, Instruction instruction) {
        InstructionEntity instructionEntity = new InstructionEntity();
        instructionEntity.setDescription(instruction.getDescription());
        instructionEntity.setStep(instruction.getStep());
        instructionEntity.setRecipeId(Integer.valueOf(String.valueOf(recipeId)));
        return instructionEntity;
    }

    private static Instruction mapToApi(InstructionEntity instructionEntity) {
        Instruction instruction = new Instruction();
        instruction.setDescription(instructionEntity.getDescription());
        instruction.setStep(instructionEntity.getStep());
        return instruction;
    }
}
