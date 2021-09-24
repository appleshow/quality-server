package com.aps.quality.service;

import com.aps.quality.entity.OperationLog;
import com.aps.quality.repository.OperationLogRepository;
import com.aps.quality.util.Const;
import com.aps.quality.util.DataUtil;
import com.aps.quality.util.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class OperationLogService {
    @Resource
    private OperationLogRepository operationLogRepository;

    public void saveLog(final Const.OperationType type, final String relatedGuid, final Object data) {
        final OperationLog operationLog = new OperationLog();
        operationLog.setOperationType(type.getCode());
        operationLog.setOperationData(DataUtil.coverObject2StrWithoutException(data));
        operationLog.setOperationRelatedGuid(relatedGuid);

        saveLog(operationLog);
    }

    public void saveLog(final Const.OperationType type, final String relatedGuid, final Object data, final ErrorMessage error) {
        saveLog(type, relatedGuid, data, error.getDescription());
    }

    public void saveLog(final Const.OperationType type, final String relatedGuid, final Object data, final String error) {
        final OperationLog operationLog = new OperationLog();
        operationLog.setOperationType(type.getCode());
        operationLog.setOperationData(DataUtil.coverObject2StrWithoutException(data));
        operationLog.setOperationRelatedGuid(relatedGuid);
        operationLog.setErrorMessage(error);

        saveLog(operationLog);
    }

    public void saveLog(final Const.OperationType type, final String relatedGuid, final Const.OperationSubType subType, final Object data) {
        final OperationLog operationLog = new OperationLog();
        operationLog.setOperationType(type.getCode());
        operationLog.setOperationSubtype(subType.getCode());
        operationLog.setOperationData(DataUtil.coverObject2StrWithoutException(data));
        operationLog.setOperationRelatedGuid(relatedGuid);

        saveLog(operationLog);
    }

    public void saveLog(final Const.OperationType type, final Const.OperationSubType subType, final String relatedGuid, final Object data) {
        final OperationLog operationLog = new OperationLog();
        operationLog.setOperationType(type.getCode());
        operationLog.setOperationSubtype(subType.getCode());
        operationLog.setOperationData(DataUtil.coverObject2StrWithoutException(data));
        operationLog.setOperationRelatedGuid(relatedGuid);

        saveLog(operationLog);
    }

    public void saveLog(final Const.OperationType type, final Const.OperationSubType subType, final String relatedGuid, final Object data, final ErrorMessage error) {
        saveLog(type, subType, relatedGuid, data, error.getDescription());
    }

    public void saveLog(final Const.OperationType type, final Const.OperationSubType subType, final String relatedGuid, final Object data, final String error) {
        final OperationLog operationLog = new OperationLog();
        operationLog.setOperationType(type.getCode());
        operationLog.setOperationSubtype(subType.getCode());
        operationLog.setOperationData(DataUtil.coverObject2StrWithoutException(data));
        operationLog.setOperationRelatedGuid(relatedGuid);
        operationLog.setErrorMessage(error);

        saveLog(operationLog);
    }

    public void saveLog(final String category, final String subcategory, final String relatedGuid, final Object data, final ErrorMessage error) {
        saveLog(category, subcategory, relatedGuid, data, error);
    }

    public void saveLog(final String category, final String subcategory, final String relatedGuid, final Object data, final String error) {
        final OperationLog operationLog = new OperationLog();
        operationLog.setCategory(category);
        operationLog.setOperationSubtype(subcategory);
        operationLog.setOperationData(DataUtil.coverObject2StrWithoutException(data));
        operationLog.setOperationRelatedGuid(relatedGuid);
        operationLog.setErrorMessage(error);

        saveLog(operationLog);
    }

    public void saveLog(final OperationLog operationLog) {
        operationLog.setStatus(1);
        if (!StringUtils.hasLength(operationLog.getErrorMessage())) {
            operationLog.setFlag("Y");
        } else {
            operationLog.setFlag("N");
        }
        log.info("call operationLogRepository.save()");
        operationLogRepository.save(operationLog);
    }

    public void saveLog(final List<OperationLog> operationLogs) {
        operationLogs.forEach(o -> {
            o.setStatus(1);
            if (!StringUtils.hasLength(o.getErrorMessage())) {
                o.setFlag("Y");
            } else {
                o.setFlag("N");
            }
        });

        log.info("call operationLogRepository.saveAll()");
        operationLogRepository.saveAll(operationLogs);
    }
}
