require 'sinatra'

set :port, 3000

post '/' do
  logger.info "from: #{params[:from]}"
  logger.info "to: #{params[:to]}"
end

