package com.wruins.web.admin;

import com.wruins.po.Tag;
import com.wruins.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String Tags(@PageableDefault(size=5,sort={"id"},direction = Sort.Direction.DESC )
                                   Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        tagService.listTag(pageable);
        return "admin/tags";
    }

    @GetMapping("/tag/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag-add";
    }

    @GetMapping("/tag/{id}/modify")
    public String editTag(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return"admin/tag-add";
    }

    @PostMapping("/tags")
    public String Post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 !=null){
            result.rejectValue("name","nameError","不能添加重复得标签");
        }
        if (result.hasErrors()){
            return "admin/tag-add";
        }
        Tag t = tagService.saveTag(tag);
        if(t==null){
            attributes.addFlashAttribute("message","添加失败");
        }else{
            attributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tag/{id}")
    public String modifyPost(@Valid Tag tag, BindingResult result,
                             @PathVariable Long id,
                             RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 !=null){
            result.rejectValue("name","nameError","不能添加重复得标签");
        }
        if (result.hasErrors()){
            return "admin/tag-add";
        }
        Tag t = tagService.updateTag(id, tag);
        if(t==null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tag/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
