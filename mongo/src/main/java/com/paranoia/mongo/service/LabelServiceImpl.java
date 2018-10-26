package com.paranoia.mongo.service;

import com.paranoia.mongo.collection.label.Label;
import com.paranoia.mongo.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/19 16:34
 */
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Override
    public Label saveOrUpdate(Label label) {
        Assert.notNull(label, "label不能为空");
        return labelRepository.save(label);
    }

    @Override
    public Label getParentByChildren(String value) {
        return labelRepository.findByValue(value);
    }

    @Override
    public List<Label> getChildrenByParent(String path) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
                                                      .withIgnoreCase(true)
                                                      .withMatcher("path", ExampleMatcher.GenericPropertyMatcher::endsWith);

        Example<Label> example = Example.of(new Label(path), exampleMatcher);
        return labelRepository.findAll(example);
    }
}
