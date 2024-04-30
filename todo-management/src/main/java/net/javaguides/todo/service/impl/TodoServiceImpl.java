package net.javaguides.todo.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.todo.dto.TodoDto;
import net.javaguides.todo.entity.Todo;
import net.javaguides.todo.exception.ResourceNotFoundException;
import net.javaguides.todo.repository.TodoRepository;
import net.javaguides.todo.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    private ModelMapper modelMapper;
    @Override
    public TodoDto addTodo(TodoDto todoDto) {

    //Convert TodoDto into Todo Jpa Entity

//        Todo todo = new Todo();
//        todo.setTitle(todoDto.getTitle());
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted(todoDto.isCompleted());

        Todo todo = modelMapper.map(todoDto, Todo.class);


    //saved this todo JPA Entity
       Todo savedTodo = todoRepository.save(todo);

    //Convert saved Todo JPA Entity object into TodoDto object

//        TodoDto savedTodoDto = new TodoDto();
//        savedTodoDto.setId(savedTodo.getId());
//        savedTodoDto.setTitle(savedTodo.getTitle());
//        savedTodoDto.setDescription(savedTodo.getDescription());
//        savedTodoDto.setCompleted(savedTodoDto.isCompleted());


        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not Found with id:" + id));
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id :" + id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo updateTodo = todoRepository.save(todo);

        return modelMapper.map(updateTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
     Todo todo =   todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));
        todoRepository.deleteById(id);
    }

    @Override
    public TodoDto completeTodo(Long id) {
       Todo todo = todoRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));
       todo.setCompleted(Boolean.TRUE);

       Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {
        Todo todo =   todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id:" + id));
        todo.setCompleted(Boolean.FALSE);
        Todo updatedTodo = todoRepository.save(todo);


        return modelMapper.map(updatedTodo, TodoDto.class);
    }
}
