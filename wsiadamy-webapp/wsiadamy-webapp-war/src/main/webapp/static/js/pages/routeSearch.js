$(function() {
	
  $(document).bind('scroll mousewheel', function() {
    var resultsNextPage = $('.nextPage');
    
    if(!resultsNextPage.length)
      return;
    
    var resultsNextPagePos = resultsNextPage.offset().top;
  
    if(resultsNextPage.hasClass('loading'))
      return;
    
    var scrollTop = $(window).scrollTop() + $(window).height();
    
    if(scrollTop > resultsNextPagePos) {
      resultsNextPage.addClass('loading');
      resultsNextPage.html('LOADING...');
      
      $.get(resultsNextPage.attr('href'), function(response) {
        resultsNextPage.remove();
        resultsNextPage = $('.nextPage');
        
        console.log(response);
        
        var aResponse = response.match(/<div class="results">([^Ĺ]*?)<!-- .results \/\/-->/);
        $('.results').append(aResponse[1]);
        console.log(aResponse[1]);
      });
    }
  });
});
