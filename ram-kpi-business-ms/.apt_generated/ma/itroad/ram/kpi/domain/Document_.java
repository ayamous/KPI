package ma.itroad.ram.kpi.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Document.class)
public abstract class Document_ {

	public static volatile SingularAttribute<Document, String> extension;
	public static volatile SingularAttribute<Document, Long> size;
	public static volatile SingularAttribute<Document, Kpi> kpi;
	public static volatile SingularAttribute<Document, String> name;
	public static volatile SingularAttribute<Document, String> dirId;
	public static volatile SingularAttribute<Document, Long> id;
	public static volatile SingularAttribute<Document, String> contentType;
	public static volatile SingularAttribute<Document, String> url;
	public static volatile SingularAttribute<Document, String> fsName;

	public static final String EXTENSION = "extension";
	public static final String SIZE = "size";
	public static final String KPI = "kpi";
	public static final String NAME = "name";
	public static final String DIR_ID = "dirId";
	public static final String ID = "id";
	public static final String CONTENT_TYPE = "contentType";
	public static final String URL = "url";
	public static final String FS_NAME = "fsName";

}

