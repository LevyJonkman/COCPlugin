package nl.levy.COCPlugin.Components;

import nl.levy.COCPlugin.COCItems.ResourceCollection;
import nl.levy.COCPlugin.COCItems.ResourceProductionLevel;
import nl.levy.COCPlugin.COCItems.ResourceType;
import nl.levy.COCPlugin.COCManager.COCManager;

import java.util.Date;

public class ResourceGeneratorComponent implements IComponent {

    public ResourceProductionLevel currentProductionLevel;

    public Date lastCleaned;
    public final ResourceType resourceType;
    private int storedResources;

    public ResourceGeneratorComponent(ResourceProductionLevel currentProductionLevel, ResourceType resourceType) {
        this.currentProductionLevel = currentProductionLevel;
        this.resourceType = resourceType;
        lastCleaned = new Date();
        storedResources = 0;
    }

    public int getProduction() {
        long time = new Date().getTime() - lastCleaned.getTime();
        return (int) Math.min(currentProductionLevel.total, (time / 1000) * currentProductionLevel.productionPerSeconde + storedResources);
    }

    public void collect(COCManager manager) {
        var production = getProduction();
        lastCleaned = new Date();
        storedResources = 0;

        var col =  switch (resourceType){
            case Elixir -> new ResourceCollection(production, 0, 0);
            case DarkElixir -> new ResourceCollection(0, production, 0);
            case Gold -> new ResourceCollection(0, 0, production);
        };

        manager.addResource(col);
    }
}
