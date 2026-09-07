import com.allancleitonppma.sscagent.application.pickingStrategies.ConditionStrategy;
import com.allancleitonppma.sscagent.application.services.InterpretationEngine;
import com.allancleitonppma.sscagent.application.usecase.ImportStockBox;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.OrderPreview;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingMap;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.infrastructure.adapters.excel.ExcelLoaderStockBox;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ConditionStrategyTest {

    @Test
    void shouldProcessConditionStrategy() throws Exception {


        // preparar Order + Infrastructure
        OrderPreview order = new OrderPreview(
                UUID.randomUUID(),
                "11070",
                100,
                "DIAS = 8",
                "123456",
                null
        );
        ImportStockBox importStockBox = new ImportStockBox(new ExcelLoaderStockBox(Path.of("C:\\Users\\allan\\Documents\\MyWorkspace\\SSCAgent\\SSCAGENT\\sscagent-desktop\\src\\main\\resources\\Data\\StockBox.xls")));

        //Instanciando um InterpretationEngine, para a posteriore interpretar a order
        InterpretationEngine engine =  new InterpretationEngine();

        // A partir da order, o interpretationEngine cria uma InterpretedOrder, que pe uma ordem com sua condition interpretada
        InterpretedOrder result =  engine.interpret(order);


        //importStockBox.StockBoxLoadAll("6005").forEach(IO::println);

        PickingMap map = new ConditionStrategy().generated(result, null, importStockBox);


        System.out.println("Mapa: " + map.getId()+"\n");
        System.out.println("Ordem de carga: \n" + result + "\n");

        for(StockBox box: map.getBoxes()){
            System.out.println(box);
            System.out.println();
        }

        assertTrue(true);
    }
}