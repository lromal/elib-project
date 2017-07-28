/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

define(['jquery','https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js'], function($, dt) {

    return (function(columnOrder,tableName) {
//        var oTable = $('#loopjq').DataTable({
        var oTable = $(tableName).DataTable({
            aLengthMenu: [[10, 15, 30], [10, 15, 30]],
            language: {
                emptyTable: "Данных нет",
                info: "Показана страница _PAGE_ из _PAGES_",
                infoEmpty: "Нет страниц",
                lengthMenu: "Показано _MENU_ записей на страницу",
                paginate: {
                    previous: 'Назад',
                    next:     'Вперед'
                },
                aria: {
                    paginate: {
                        previous: 'Previous',
                        next:     'Next'
                    }
                }
            },
            jQueryUI: true,
            searching: false
        });
        oTable.order( [ columnOrder, 'asc' ] ).draw();
    });

});