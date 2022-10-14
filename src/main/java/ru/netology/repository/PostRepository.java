
package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Stub
public class PostRepository {
  private long counter;
  private ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();

  public List<Post> all() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) throws NotFoundException {
    if (post.getId() == 0) {
      counter++;
      post.setId(counter);
      posts.put(counter, post);
      return posts.get(counter);
    } else if (posts.containsKey(post.getId())) {
      posts.replace(post.getId(), post);
      return posts.get(post.getId());
    } else {
      throw new NotFoundException("Id not found!");
    }
  }

  public void removeById(long id) {
    if (posts.containsKey(id)) {
      posts.remove(id);
    } else {
      throw new NotFoundException("Id not found!");
    }
  }
}