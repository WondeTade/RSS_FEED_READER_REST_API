package com.wonde.feedrepository;

import com.wonde.feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Integer> {
    //integer specifies the primary key in the table
}

