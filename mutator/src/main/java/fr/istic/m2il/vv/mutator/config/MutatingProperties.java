package fr.istic.m2il.vv.mutator.config;

import fr.istic.m2il.vv.mutator.mutant.MutantType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MutatingProperties {


    static  String[] mutators
            ;
    public static List<MutantType> mutantsToAnalysis() throws IOException {
        List<MutantType> mutantTypes = new ArrayList<>();
        String mutatorsPropertie = ApplicationProperties.getInstance().getApplicationPropertiesFile().getProperty(ConfigOption.MUTATORS.toString());
        if(mutatorsPropertie != null){
            mutatorsPropertie = mutatorsPropertie.trim();
            mutators = mutatorsPropertie.split(",");
            if(mutatorsPropertie != null && mutators[0].equals("")){
                for(MutantType mutantType:MutantType.values()){
                    mutantTypes.add(mutantType);
                }
            }
            else if (mutantTypes.size() == 0){
                for(String mutator: mutators){
                    for(MutantType mutantType:MutantType.values()){
                        mutator = mutator.trim();
                        if(mutator.matches(mutantType.name()))
                            mutantTypes.add(MutantType.valueOf(mutator));
                    }
                }
            }
        }
        else{
            for(MutantType mutantType:MutantType.values()){

                    mutantTypes.add(mutantType);
            }
        }


        return mutantTypes;
    }
}
