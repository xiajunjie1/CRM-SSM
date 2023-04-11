package com.maker.crm.service.clue;

import com.maker.crm.model.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {
    public int addClueActivityRelationBatch(List<ClueActivityRelation> clueActivityRelations)throws Exception;
    public int removeClueActivityRelation(ClueActivityRelation clueActivityRelation)throws Exception;
}
