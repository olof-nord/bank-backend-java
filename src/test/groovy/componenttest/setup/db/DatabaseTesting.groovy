package componenttest.setup.db

import org.h2.tools.Server
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate

import javax.persistence.EntityManager

trait DatabaseTesting {

    private static List<String> TABLES = ["customer", "address"]

    private static Server h2server = Server
        .createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092")
        .start()

    @Autowired
    private EntityManager entityManager

    @Autowired
    private TransactionTemplate transactionTemplate

    @Before
    void reset(){
        transactionTemplate.execute({
            status -> TABLES.each {
                entityManager.createNativeQuery("DELETE FROM $it".toString()).executeUpdate()
            }
        })
    }

}
