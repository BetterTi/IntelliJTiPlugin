

Development Notes

The protocol for communicating with an iOS device is located in the Titanium Debugger Protocol documentation. This
is fairly straightforward in describing the commands you can send.

The protocol for android was a little different. There is a translation server going from Node.js debugger protocol to
titanium debugger protocol. I can connect to the debugger protocol, but I need to send commands formatted as
if I was communicating with a node.js debugger.


### Android V8 Debugging Comms Example

Client: {"seq":19,"type":"request","command":"version"}
Server: {"seq":0,"request_seq":19,"type":"response","command":"version","success":true,"body":{"V8Version":"3.9.24.29"},"refs":[],"running":false}
Client: {"seq":20,"type":"request","command":"backtrace","arguments":{"inlineRefs":true}}
Client: {"seq":21,"type":"request","command":"flags","arguments":{"flags":[{"name":"breakOnCaughtException","value":false},{"name":"breakOnUncaughtException","value":false}]}}
Server: {"seq":1,"request_seq":20,"type":"response","command":"backtrace","success":true,"body":{"fromFrame":0,"toFrame":1,"totalFrames":1,"frames":[{"type":"frame","index":0,"receiver":{"ref":0,"type":"function","name":"Module","inferredName":"","scriptId":30},"func":{"ref":3,"type":"function","name":"","inferredName":"Module.runModule","scriptId":30},"script":{"ref":2},"constructCall":false,"atReturn":false,"debuggerFrame":false,"arguments":[{"name":"source","value":{"ref":4,"type":"string","value":"// this sets the background color of the master UIView (when there are no window... (length: 2331)"}},{"name":"filename","value":{"ref":5,"type":"string","value":"Resources/app.js"}},{"name":"activityOrService","value":{"ref":6,"type":"object","className":"Activity"}}],"locals":[{"name":"module","value":{"ref":7,"type":"undefined"}},{"name":"isService","value":{"ref":7,"type":"undefined"}},{"name":"id","value":{"ref":7,"type":"undefined"}}],"position":1028,"line":36,"column":28,"sourceLineText":"Module.runModule = function (source, filename, activityOrService) {","scopes":[{"type":1,"index":0},{"type":3,"index":1},{"type":0,"index":2}],"text":"#00 function Module(id, parent, context) {\n\tthis.id = id;\n\tthis.exports = {};\n\tthis.parent = parent;\n\tthis.context = context;\n\n\tthis.filename = null;\n\tthis.loaded = false;\n\tthis.exited = false;\n\tthis.children = [];\n\tthis.wrapperCache = {};\n}.runModule(source=// this sets the background color of the master UIView (when there are no window... (length: 2331), filename=Resources/app.js, activityOrService=#<Activity>) ti:/module.js line 37 column 29 (position 1029)"}]},"refs":[{"handle":2,"type":"script","name":"ti:/module.js","id":30,"lineOffset":0,"columnOffset":0,"lineCount":423,"sourceStart":"(function (exports, require, module, __filename, __dirname, Titanium, Ti, global","sourceLength":12494,"scriptType":2,"compilationType":0,"context":{"ref":1},"text":"ti:/module.js (lines: 423)"}],"running":false}
        {"seq":2,"request_seq":21,"type":"response","command":"flags","success":true,"body":{"flags":[{"name":"breakOnCaughtException","value":false},{"name":"breakOnUncaughtException","value":false}]},"refs":[],"running":false}
Client: {"seq":23,"type":"request","command":"scripts","arguments":{"types":4,"includeSource":true}}
Client: {"seq":24,"type":"request","command":"flags","arguments":{"flags":[{"name":"breakOnCaughtException","value":true},{"name":"breakOnUncaughtException","value":true}]}}
Client: {"seq":25,"type":"request","command":"setbreakpoint","arguments":{"condition":"","line":20,"column":0,"type":"script","enabled":true,"target":"app.js"}}
Server: {"seq":5,"request_seq":25,"type":"response","command":"setbreakpoint","success":true,"body":{"type":"scriptName","breakpoint":1,"script_name":"app.js","line":20,"column":0,"actual_locations":[]},"refs":[],"running":false}
Client: {"seq":26,"type":"request","command":"flags","arguments":{"flags":[{"name":"breakOnCaughtException","value":true},{"name":"breakOnUncaughtException","value":true}]}}
Server: {"seq":6,"request_seq":26,"type":"response","command":"flags","success":true,"body":{"flags":[{"name":"breakOnCaughtException","value":true},{"name":"breakOnUncaughtException","value":true}]},"refs":[],"running":false}
Client: {"seq":27,"type":"request","command":"continue"}
Server: {"seq":7,"request_seq":27,"type":"response","command":"continue","success":true,"running":true}
        {"seq":8,"type":"event","event":"afterCompile","success":true,"body":{"script":{"handle":1,"type":"script","name":"app.js","id":36,"lineOffset":0,"columnOffset":0,"lineCount":101,"sourceStart":"// this sets the background color of the master UIView (when there are no window","sourceLength":2331,"scriptType":2,"compilationType":0,"context":{"ref":0},"text":"app.js (lines: 101)"}},"refs":[{"handle":0,"type":"context","text":"#<ContextMirror>"}],"running":true}
        {"seq":9,"type":"event","event":"break","body":{"invocationText":"[anonymous]()","sourceLine":20,"sourceColumn":0,"sourceLineText":"var label1 = Titanium.UI.createLabel({","script":{"id":36,"name":"app.js","lineOffset":0,"columnOffset":0,"lineCount":101},"breakpoints":[1]}}
Client: {"seq":28,"type":"request","command":"scripts","arguments":{"includeSource":true,"ids":[36]}}    
Client: {"seq":29,"type":"request","command":"backtrace","arguments":{"inlineRefs":true}}
    
    
    
### Android V8 Example of halting a titanium app
{"seq":1,"type":"request","command":"evaluate","arguments":{"disable_break":true,"expression":"Ti.API.terminate()","inlineRefs":true,"global":true}}