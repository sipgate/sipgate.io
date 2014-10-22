require 'sinatra'
require 'builder'

set :port, 3000

post '/' do
  logger.info "from: #{params[:from]}"
  logger.info "to: #{params[:to]}"

  headers 'Content-Type' => "application/xml"
  xml = Builder::XmlMarkup.new(:indent => 2)
  xml.instruct!
  xml.Response{xml.Dial{xml.Number("4915799912345")}}


end

