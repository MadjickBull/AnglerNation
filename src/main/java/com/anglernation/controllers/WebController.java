package com.anglernation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;

import java.util.*;

@Controller
public class WebController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiBase = "http://localhost:8081/api/v1";

    // ==================== DASHBOARD ====================
    @GetMapping("/")
    public String dashboard(Model model) {
        Map<String, Long> stats = getStats();
        model.addAttribute("title", "Dashboard");
        model.addAttribute("stats", stats);
        return "dashboard";
    }

    private Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        String[] domains = {"rods", "lures", "lines", "locations", "fish", "catches", "posts", "comments", "users"};
        for (String domain : domains) {
            try {
                ResponseEntity<List> response = restTemplate.exchange(
                    apiBase + "/" + domain + "?size=1",
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List>() {}
                );
                // Get total from headers or make separate call
                stats.put(domain, response.getHeaders().getFirst("X-Total-Count") != null ? 
                    Long.parseLong(response.getHeaders().getFirst("X-Total-Count")) : 0L);
            } catch (Exception e) {
                stats.put(domain, 0L);
            }
        }
        return stats;
    }

    // ==================== GENERIC DOMAIN ROUTES ====================
    
    // List view for any domain
    @GetMapping("/{domain}")
    public String domainList(
            @org.springframework.web.bind.annotation.PathVariable String domain,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            Model model) {
        
        String domainTitle = capitalize(domain);
        String icon = getIcon(domain);
        
        try {
            String url = apiBase + "/" + domain + "?page=" + page + "&size=" + size;
            if (search != null && !search.isEmpty()) {
                url += "&search=" + search;
            }
            
            ResponseEntity<PagedResponse> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PagedResponse>() {}
            );
            
            PagedResponse paged = response.getBody();
            
            model.addAttribute("title", domainTitle);
            model.addAttribute("domain", domain);
            model.addAttribute("icon", icon);
            model.addAttribute("content", paged != null ? paged.getContent() : Collections.emptyList());
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("search", search);
            model.addAttribute("totalPages", paged != null ? paged.getTotalPages() : 0);
            model.addAttribute("totalElements", paged != null ? paged.getTotalElements() : 0);
            
        } catch (Exception e) {
            model.addAttribute("title", domainTitle);
            model.addAttribute("domain", domain);
            model.addAttribute("icon", icon);
            model.addAttribute("content", Collections.emptyList());
            model.addAttribute("page", 0);
            model.addAttribute("size", size);
            model.addAttribute("search", search);
            model.addAttribute("totalPages", 0);
            model.addAttribute("totalElements", 0);
        }
        
        return "domain/list";
    }

    // Detail view
    @GetMapping("/{domain}/{id}")
    public String domainDetail(
            @org.springframework.web.bind.annotation.PathVariable String domain,
            @org.springframework.web.bind.annotation.PathVariable String id,
            Model model) {
        
        String domainTitle = capitalize(domain);
        String icon = getIcon(domain);
        
        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                apiBase + "/" + domain + "/" + id,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map>() {}
            );
            
            model.addAttribute("item", response.getBody());
            model.addAttribute("title", domainTitle);
            model.addAttribute("domain", domain);
            model.addAttribute("icon", icon);
            
        } catch (Exception e) {
            model.addAttribute("item", Collections.emptyMap());
            model.addAttribute("title", domainTitle);
            model.addAttribute("domain", domain);
            model.addAttribute("icon", icon);
        }
        
        return "domain/detail";
    }

    // Create form
    @GetMapping("/{domain}/new")
    public String domainNew(
            @org.springframework.web.bind.annotation.PathVariable String domain,
            Model model) {
        
        String domainTitle = capitalize(domain);
        String icon = getIcon(domain);
        
        model.addAttribute("title", "New " + domainTitle);
        model.addAttribute("domain", domain);
        model.addAttribute("icon", icon);
        model.addAttribute("item", new HashMap<>());
        
        return "domain/form";
    }

    // Edit form
    @GetMapping("/{domain}/{id}/edit")
    public String domainEdit(
            @org.springframework.web.bind.annotation.PathVariable String domain,
            @org.springframework.web.bind.annotation.PathVariable String id,
            Model model) {
        
        return domainDetail(domain, id, model); // Same as detail, template will show form in edit mode
    }

    // Helper methods
    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private String getIcon(String domain) {
        return switch (domain.toLowerCase()) {
            case "rods" -> "bi-vector-pen";
            case "lures" -> "bi-bug";
            case "lines" -> "bi-slash-circle";
            case "locations" -> "bi-geo-alt";
            case "fish" -> "bi-trophy";
            case "catches" -> "bi-journal-check";
            case "posts" -> "bi-chat-left-text";
            case "comments" -> "bi-chat";
            case "users" -> "bi-people";
            default -> "bi-box";
        };
    }

    // DTO for paginated API responses
    public static class PagedResponse<T> {
        private List<T> content;
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
        private boolean last;
        private boolean first;
        
        // Getters and setters
        public List<T> getContent() { return content; }
        public void setContent(List<T> content) { this.content = content; }
        public int getPage() { return page; }
        public void setPage(int page) { this.page = page; }
        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }
        public long getTotalElements() { return totalElements; }
        public void setTotalElements(long totalElements) { this.totalElements = totalElements; }
        public int getTotalPages() { return totalPages; }
        public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
        public boolean isLast() { return last; }
        public void setLast(boolean last) { this.last = last; }
        public boolean isFirst() { return first; }
        public void setFirst(boolean first) { this.first = first; }
    }
}
