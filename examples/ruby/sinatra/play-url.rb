require 'sinatra'
require 'builder'

set :port, 3000

post '/' do
  logger.info "from: #{params[:from]}"
  logger.info "to: #{params[:to]}"

  headers 'Content-Type' => "application/xml"
  xml = Builder::XmlMarkup.new(:indent => 2)
  xml.instruct!
  xml.Response{xml.Play{xml.Url("http://www.example.com/example.wav")}}


end

