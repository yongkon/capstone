(function ($) {
    "use strict";

     // Spinner
 /*   
     var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();
  */

    
    // Initiate the wowjs
    new WOW().init();

    
    // Dropdown on mouse hover
    const $dropdown = $(".dropdown");
    const $dropdownToggle = $(".dropdown-toggle");
    const $dropdownMenu = $(".dropdown-menu");
    const showClass = "show";
    
    $(window).on("load resize", function() {
        if (this.matchMedia("(min-width: 992px)").matches) {
            $dropdown.hover(
            function() {
                const $this = $(this);
                $this.addClass(showClass);
                $this.find($dropdownToggle).attr("aria-expanded", "true");
                $this.find($dropdownMenu).addClass(showClass);
            },
            function() {
                const $this = $(this);
                $this.removeClass(showClass);
                $this.find($dropdownToggle).attr("aria-expanded", "false");
                $this.find($dropdownMenu).removeClass(showClass);
            }
            );
        } else {
            $dropdown.off("mouseenter mouseleave");
        }
    });
    
    
    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Facts counter
    $('[data-toggle="counter-up"]').counterUp({
        delay: 10,
        time: 2000
    });


    // Modal Video
    $(document).ready(function () {
        var $videoSrc;
        $('.btn-play').click(function () {
            $videoSrc = $(this).data("src");
        });
        console.log($videoSrc);

        $('#videoModal').on('shown.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
        })

        $('#videoModal').on('hide.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc);
        })
    });


    // Testimonials carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        center: true,
        margin: 24,
        dots: true,
        loop: true,
        nav : false,
        responsive: {
            0:{
                items:1
            },
            768:{
                items:2
            },
            992:{
                items:3
            }
        }
    });


})(jQuery);

    // function that retrieve current page's url hash
    function getCurrentHash() {
        const hash = window.location.hash;
        return hash ? hash.substring(1) : '';
    }

    // function that set the current active menu
    function setActiveMenu() {
        const currentHash = getCurrentHash();
        const navLinkEls = document.querySelectorAll('.nav_link');
        navLinkEls.forEach(navLinkEl => {
            const href = navLinkEl.getAttribute('href');
            const fileName = href.split('.')[0]; // get file name
            const currentFileName = window.location.pathname.split('/').pop().split('.')[0];

            if (fileName === currentFileName) {
                navLinkEl.classList.add('active');
            } else {
                navLinkEl.classList.remove('active');
            }
        });
    }

    // menu click event handler
    function handleMenuClick(event) {
        event.preventDefault(); // cancel the basic acting

        const targetHref = this.getAttribute('href');
        if (targetHref) {
            window.location.href = targetHref; // move to the page
        }
    }

    // init and register a event
    function init() {
        setActiveMenu(); // current active menu

        const navLinkEls = document.querySelectorAll('.nav_link');
        navLinkEls.forEach(navLinkEl => {
            navLinkEl.addEventListener('click', handleMenuClick);
        });
    }

    // init when the page is loading
    window.addEventListener('DOMContentLoaded', init);