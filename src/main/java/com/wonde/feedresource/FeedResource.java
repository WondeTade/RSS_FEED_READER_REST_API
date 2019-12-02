package com.wonde.feedresource;

import java.util.List;

import com.wonde.feed.Feed;
import com.wonde.feedrepository.FeedRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feeds")
public class FeedResource {

    @Autowired
    private FeedRepository feedRepository;

    @GetMapping
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public List<Feed> getFeedLists() {

        return feedRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Feed get(@PathVariable Integer id) {

        return feedRepository.getOne(id);
    }

    @PostMapping
    public Feed create(@RequestBody final Feed feed) {

        return feedRepository.saveAndFlush(feed);
    }

    @RequestMapping(value = {"id"}, method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
    //Need to check for children records before deleting
        feedRepository.deleteById(id);
    }

    @RequestMapping(value = {"id"}, method = RequestMethod.PUT)
    public Feed update(@PathVariable Integer id, @RequestBody Feed feed) {
        Feed existingFeed = feedRepository.getOne(id);
        BeanUtils.copyProperties(feed, existingFeed, "id"); //ignores id while copying attributes
        return feedRepository.saveAndFlush(existingFeed);
    }
}


