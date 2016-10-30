package com.robohorse.robopojogenerator.generator.postprocessors;

import com.robohorse.robopojogenerator.generator.common.ClassDecorator;
import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.models.GenerationModel;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by vadim on 25.09.16.
 */
public class CommonJavaPostProcessor extends JavaPostProcessor {
    @Inject
    public CommonJavaPostProcessor() {
    }

    @Override
    public String proceedClassBody(ClassItem classItem, GenerationModel generationModel) {
        final StringBuilder classBodyBuilder = new StringBuilder();
        final StringBuilder classMethodBuilder = new StringBuilder();
        final Map<String, ClassDecorator> classFields = classItem.getClassFields();

        for (String objectName : classFields.keySet()) {
            final String classItemValue = classFields.get(objectName).getJavaItem();

            classBodyBuilder.append(classTemplateHelper.createFiled(
                    classItemValue,
                    objectName,
                    classItem.getAnnotation()));

            if (generationModel.isUseSetters()) {
                classMethodBuilder.append(ClassTemplate.NEW_LINE);
                classMethodBuilder.append(classTemplateHelper.createSetter(
                        objectName,
                        classItemValue));

            }
            if (generationModel.isUseGetters()) {
                classMethodBuilder.append(ClassTemplate.NEW_LINE);
                classMethodBuilder.append(classTemplateHelper.createGetter(
                        objectName,
                        classItemValue));
            }
        }
        classBodyBuilder.append(classMethodBuilder);
        return classBodyBuilder.toString();
    }

    @Override
    public String createClassTemplate(ClassItem classItem, String classBody) {
        return classTemplateHelper.createClassBody(classItem, classBody);
    }
}