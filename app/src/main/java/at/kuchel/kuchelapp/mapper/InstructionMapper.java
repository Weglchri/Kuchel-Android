package at.kuchel.kuchelapp.mapper;

import java.util.ArrayList;
import java.util.List;

import at.kuchel.kuchelapp.api.InstructionResponse;
import at.kuchel.kuchelapp.api.RecipeResponse;
import at.kuchel.kuchelapp.model.Instruction;
import at.kuchel.kuchelapp.model.Recipe;

/**
 * Created by bernhard on 19.03.2018.
 */

public class InstructionMapper {

    public static List<Instruction> map(Long recipeId, List<InstructionResponse> instructionResponses) {
        List<Instruction> instructions = new ArrayList<>();
        for (InstructionResponse instructionResponse : instructionResponses) {
            instructions.add(map(recipeId, instructionResponse));
        }

        return instructions;
    }

    private static Instruction map(Long recipeId, InstructionResponse instructionResponse) {
        Instruction instruction = new Instruction();
        instruction.setDescription(instructionResponse.getDescription());
        instruction.setStep(instructionResponse.getStep());
        instruction.setRecipeId(Integer.valueOf(String.valueOf(recipeId)));
        return instruction;
    }
}
