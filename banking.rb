require_relative "validity"
require 'bigdecimal'
class Bank
  include Validity
end

b = Bank.new
FILE = "users.CSV"
Users = []

if File.exist?(FILE)
  File.foreach(FILE) do |data|
  name, email, password, balance = data.chomp.split('|')
  Users << [name, email, password, balance.to_i,[]]
end

end

login = nil
current = nil

loop do
  puts "--- Main Menu ---"

  puts "1. register"
  puts "2. login"
  puts "3. Banking Operations (After Login):"
  puts "4. Exit"

  print " Enter number: "
  choice = gets.to_i

  case choice
  when 1
    begin
      puts "Enter your name"
      name = gets.chomp
      b.validate_name(name)

      puts "Enter email"
      email = gets.chomp
      b.validate_email(email)

      puts "Enter password"
      password = gets.chomp
      b.validate_pass(password)

      puts "Enter password for confirmation"
      confirm = gets.chomp
      b.validate_password(password, confirm)

      Users[Users.length] = [name, email, password, 0, []]

      File.open(FILE, "a") do |file|
        file.puts "#{name}|#{email}|#{password}|0"
     end

      puts "registration complete"
    rescue => e
      puts e.message
    end

  when 2
    puts "Enter email for login"
    email = gets.chomp
    puts "Enter password for login"
    password = gets.chomp
    
    got = 0
    Users.each do |user|
      if user[1] == email && user[2] == password
       
        current = user
        login = 1
        got = 1
        break
      end
    end

    if got == 1
       puts "Login successful"
    else
      puts "login failed"
    end

  when 3

    if login == nil
      puts "login not complete"
      next
    end
    
    while login

    puts "see operation after login"
    puts "1. Deposit Money"
    puts "2. Withdraw Money"
    puts "3. Check Balance"
    puts "4. View Account Details"
    puts "5. Change Password"
    puts "6. Transaction History"
    puts "7. Account Statement(Date-wise)"
    puts "8. Logout: Exit the banking menu"

    puts "Enter the number"
    choice = gets.chomp

    case choice
    when "1"
      
     begin
      puts "Enter amount for deposit"
      amount = gets.to_i
      b.validate_amount(amount)
      current[3] = current[3] + BigDecimal(amount.to_s)

      current[4] << {
        date: Time.now,
        type: "Deposit of amount: #{amount}"
      }

      puts "Deposit successful of #{amount}"
      puts "New balance become"
      puts current[3]
      
     rescue => e 
      puts e.message
     end

    when "2"
    begin
      puts "Enter amount for withdraw"
      amount = gets.to_i

      b.validate_amount(amount)

      if amount <= current[3]
        current[3] = current[3] - amount

        current[4] << {
        date: Time.now,
        type: "Withdrawal of amount: #{amount}"
      }


        puts "Withdrawl success of amount #{amount}"
        puts "Amount after withdrawl"
        puts current[3]

      else
        puts "Balance is not sufficient, please check balance"
        
      end

    rescue => e 
      puts e.message
    end
      
    when "3"

     begin
        puts "Check Balance"
      puts current[3]
     rescue => e
      puts e.message
     end
     
    when "4"

      begin
      puts "Name: #{current[0]}"
      puts "Email: #{current[1]}"
      puts "Balance: #{current[3]}"

      rescue => e
        puts e.message
      end
    
    when "5"
       begin
        puts "Change password"
        puts "Enter new password"
        newpassword = gets.chomp

        puts "Confirm password"
        confirmpassword = gets.chomp
        b.validate_password(newpassword, confirmpassword)

        current[2] = newpassword
        puts "password changed"
      rescue => e
        puts e.message   
     end
     
    when "6"
      puts "Transection history"

      if current[4].empty? 
          puts "No transaction"
          else
          current[4].each do |time|
          puts "on date: #{time[:date]}, type: #{time[:type]}"
        end
      end

    when "7"
      puts "Enter date in format (yyyy-mm-dd)"
      date = gets.chomp

      find = false

      current[4].each do |time|
        if time[:date].strftime("%Y-%m-%d") == date
          puts "on date: #{time[:date]}, type: #{time[:type]}"
          find = true
        else
           puts "no transaction on this date try again"
        end   
      end
       when "8"
        login = nil
        current = nil
        puts "Logged out successfully."
        break 

      else
        puts "Invalid choice."
      end

    end 

  when 4
    puts "Exiting program"
    break  

  else
    puts "Invalid choice."
  end

end
