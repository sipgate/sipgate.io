:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_dispatch)).
:- use_module(library(http/http_client)).

:- http_handler('/', process_request, []).

server :- http_server(http_dispatch, [port(3000)]).

process_request(Request) :-
	member(method(post), Request), !,
	http_read_data(Request, Data, []),
	Data = [From|[To|[Direction]]],
	print_message(informational, From),
	print_message(informational, To),
	print_message(informational, Direction),
	answer.

answer :-
	format('Content-type: text/plain~n~n', []),
	print('Over and out\n').
