var fs = require('fs');
var Schema = require('./lib/coder/schema');
Schema.loadSchema('./Messages.desc');

var d = Schema.makeSchemaDictionary();

fs.writeFileSync('./MessagesDic.json', JSON.stringify(d));

console.log(d);



