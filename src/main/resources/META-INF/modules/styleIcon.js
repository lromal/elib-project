/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

define(['jquery'], function($) {

    return (function(iconNumber) {

        if(iconNumber === 'adminLink') {
            $('#adminLink').css('background-color', '#1fc173');
            $('#adminLink').css('border', '1px solid #66afe9');
            $('#adminLink').css('box-shadow', '0 0 10px rgba(0, 198, 255, 0.3)');
        }
        if(iconNumber === 'homeLink') {
            $('#homeLink').css('background-color', '#f7f7f7');
            $('#homeLink').css('border', '1px solid #66afe9');
            $('#homeLink').css('box-shadow', '0 0 10px rgba(0, 198, 255, 0.3)');
        }
        if(iconNumber === 'bookLink') {
            $('#bookLink').css('background-color', '#f7f7f7');
            $('#bookLink').css('border', '1px solid #66afe9');
            $('#bookLink').css('box-shadow', '0 0 10px rgba(0, 198, 255, 0.3)');
        }
        if(iconNumber === 'authorLink') {
            $('#authorLink').css('background-color', '#f7f7f7');
            $('#authorLink').css('border', '1px solid #66afe9');
            $('#authorLink').css('box-shadow', '0 0 10px rgba(0, 198, 255, 0.3)');
        }
        
        
        
    });

});