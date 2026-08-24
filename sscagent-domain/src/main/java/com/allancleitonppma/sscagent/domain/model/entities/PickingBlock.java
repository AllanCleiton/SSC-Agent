package com.allancleitonppma.sscagent.domain.model.entities;

import com.allancleitonppma.sscagent.domain.model.enums.QuantityUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class PickingBlock
{
    public String blockId  = UUID.randomUUID().toString();
    public int productCode;
    public Double requestedQuantity;
    public int requestedBoxes;
    public Double plannedQuantity;
    public List<PickingLine> lines = new ArrayList<>();
    public int plannedBoxes = lines.stream()
                                .mapToInt(line -> line.boxCount)
                                .sum();
    public int shortageBoxes = Math.max(0, requestedBoxes - plannedBoxes);
    public QuantityUnit quantityUnit;
    public String instructionText;
    public InstructionInterpretation interpretation = new InstructionInterpretation();
    public List<ParsedCriterion> criteria = interpretation.criteria;
    public List<OrderAllocation> allocations = new ArrayList<>();
    public Double shortage = Math.max(0, requestedQuantity - plannedQuantity);
}