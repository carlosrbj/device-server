package br.com.hsob.scannerserver.repository;

import br.com.hsob.scannerserver.utils.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.logging.Logger;

/**
 * @author carlos
 */
public abstract class AbstractService {
    @Autowired
    @Qualifier(ScannerServerMongoDb.BASE_MONGO_CONECTION)
    protected MongoTemplate hsobdb;

    @Autowired
    protected ConvertData convertData;

    protected final Logger logger = Logger.getLogger(AbstractService.class.getName());
}

