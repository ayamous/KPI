package ma.itroad.ram.kpi.service.support;

import ma.itroad.ram.kpi.common.api.messaging.enums.SmsContentType;
import ma.itroad.ram.kpi.common.api.messaging.utils.CodeUtils;
import org.springframework.stereotype.Service;

@Service
public class SmsContent implements MessagingContent<SmsContentType> {

    private static final String template = "Votre code d'activation est %s";

    @Override
    public String prepare(SmsContentType type) {
        switch (type) {
            case ACTIVATION:
                return String.format(template, CodeUtils.generateCustomOTP());
            default:
                return null;
        }
    }

    @Override
    public String prepare(SmsContentType type, Object... params) {
        switch (type) {
            case ACTIVATION:
                return String.format(template, params);
            default:
                return null;
        }
    }
}
