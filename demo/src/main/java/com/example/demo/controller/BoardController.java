package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.BoardListReplyCountDto;
import com.example.demo.dto.PageRequestDto;
import com.example.demo.dto.PageResponseDto;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.net.URLEncoder;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/remove")
    public String remove(Long id,PageRequestDto pageRequestDto, RedirectAttributes redirectAttributes){
        boardService.remove(id);
        return "redirect:/board/list";
    }


    @PostMapping("/modify")
    public String submitModify(@Valid BoardDto boardDto, Errors errors,
                               PageRequestDto pageRequestDto,RedirectAttributes redirectAttributes){
        if(errors.hasErrors()){
            redirectAttributes.addFlashAttribute("errors",errors.getAllErrors());
            return "redirect:/board/modify?"+pageRequestDto.getLink();
        }
        boardService.modify(boardDto);
        redirectAttributes.addFlashAttribute("result","수정완료");
        return "redirect:/board/read?id="+boardDto.getBoard_id();
    }


    @GetMapping("/modify")
    public String modifyForm(Long id, PageRequestDto pageRequestDto,Model model){
        BoardDto boardDto = boardService.readOne(id);
        model.addAttribute("pageRequestDto",pageRequestDto);
        model.addAttribute("dto",boardDto);
        return "board/modify";
    }

    @GetMapping("/read")
    public String read(Long id, PageRequestDto pageRequestDto,Model model){
        BoardDto boardDto = boardService.readOne(id);
        model.addAttribute("pageRequestDto",pageRequestDto);
        model.addAttribute("dto",boardDto);
        return "board/read";
    }

    @PostMapping("/register")
    public String submitRegister(@Valid BoardDto boardDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            return "redirect:/board/register";
        }
        Long id = boardService.register(boardDto);


        redirectAttributes.addFlashAttribute("result",id);
        return "redirect:/board/list";
    }

    @GetMapping("/register")
    public String registerForm(){
        return "board/register";
    }


    @GetMapping("/list")
    public String list(@ModelAttribute PageRequestDto pageRequestDto, Model model){

        //PageResponseDto<BoardDto> responseDto = boardService.list(pageRequestDto);
        PageResponseDto<BoardListReplyCountDto> result = boardService.listWithReplyCount(pageRequestDto);
        model.addAttribute("responseDTO",result);
        return "board/list";
    }
}
