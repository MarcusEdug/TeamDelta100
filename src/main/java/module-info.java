module com.example.teamdelta100 {
    requires javafx.controls;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires hibernate.entitymanager;
    exports com.example.teamdelta100;
    opens com.example.teamdelta100.entities to org.hibernate.orm.core;
    exports com.example.teamdelta100.entities;
}