/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$('#datatable1').DataTable( {
        scrollY: 500,
        paging: false
    } );
    
    $('#datatable1').dataTable( {
  "info": false
} );

$(document).ready(function() {
    $('#datatable1').DataTable( {
        scrollY: 500,
        paging: false
    } );
    $('#TestJquery').dataTable( {
  "info": false
} );
    var table = $('#datatable1').DataTable( {
        lengthChange: false,
        buttons: [ 'copy', 'excel', 'pdf', 'colvis' ]
    } );
 
    table.buttons().container()
        .insertBefore( '#example_filter' );
} );