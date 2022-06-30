package ma.itroad.ram.kpi.service.support;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ma.itroad.ram.kpi.common.api.messaging.enums.MailContentType;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@AllArgsConstructor
public class MailContent implements MessagingContent<MailContentType> {
    private static final String TEMPLATES_FOLDER = "templates/";
    private static final String TEMPLATES_EXTENSION = ".ftl";
    private final Configuration freeMarkerConfiguration;

    @PostConstruct
    public void init() {
        freeMarkerConfiguration.setClassForTemplateLoading(this.getClass(), "/");
    }

    @Override
    @SneakyThrows
    public String prepare(MailContentType type, Object params) {
        return prepare("sharing.ftl", params);
    }

    @Override
    @SneakyThrows
    public String prepare(String templateName, Object params) {
        String htmlText = null;
        Template template = null;
        if (!templateName.endsWith(TEMPLATES_EXTENSION)) {
            templateName = templateName.concat(TEMPLATES_EXTENSION);
        }
        try {
            template = freeMarkerConfiguration.getTemplate(TEMPLATES_FOLDER.concat(templateName) );
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return htmlText;

    }


}
