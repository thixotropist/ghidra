/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ghidra.app.util.bin.format.elf.relocation;

import java.util.*;

import ghidra.app.util.bin.format.elf.*;
import ghidra.program.model.address.Address;

class RISCV_ElfRelocationContext extends ElfRelocationContext {

	protected RISCV_ElfRelocationContext(ElfRelocationHandler handler, ElfLoadHelper loadHelper,
			Map<ElfSymbol, Address> symbolMap) {
		super(handler, loadHelper, symbolMap);
	}

	/**
	 * Find the HI20 relocation whose offset matches the value of of the specified symbol.
	 * @param hi20Symbol ELF symbol which corresponds to HI20 relocation
	 * @return matching relocation or null if not found
	 */
	ElfRelocation getHi20Relocation(ElfSymbol hi20Symbol) {

		Long symValue = hi20Symbol.getValue();

		// Search for first relocation within table whose offset matches the specified hi20Symbol value
		// and whose type is appropriate for a Lo12 backreference
		ElfRelocation[] relocations = relocationTable.getRelocations();
		// Search the entire Elf relocation table for a matching hi20Symbol
		for (ElfRelocation r : relocations) {
			int type = r.getType();
			if ((r.getOffset() == symValue) &&
				(type == RISCV_ElfRelocationConstants.R_RISCV_PCREL_HI20) ||
				(type == RISCV_ElfRelocationConstants.R_RISCV_GOT_HI20)) {
					return r;
			}
		}
	return null; // relocation not found
	}
}
