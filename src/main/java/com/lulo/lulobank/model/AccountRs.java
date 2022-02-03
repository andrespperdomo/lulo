package com.lulo.lulobank.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountRs {
   private Account account;
   private List<String> violations = new ArrayList<String>();
}
